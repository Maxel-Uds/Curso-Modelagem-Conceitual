package com.maxel.cursomc.service;

import com.maxel.cursomc.domain.Cidade;
import com.maxel.cursomc.domain.Cliente;
import com.maxel.cursomc.domain.Endereco;
import com.maxel.cursomc.domain.enums.Perfil;
import com.maxel.cursomc.domain.enums.TipoCliente;
import com.maxel.cursomc.dto.ClienteNewDTO;
import com.maxel.cursomc.dto.ClienteUpdateDTO;
import com.maxel.cursomc.repositories.ClienteRepository;
import com.maxel.cursomc.repositories.EnderecoRepository;
import com.maxel.cursomc.security.UserSpringSecurity;
import com.maxel.cursomc.service.exceptions.AuthorizationException;
import com.maxel.cursomc.service.exceptions.DataIntegrityException;
import com.maxel.cursomc.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private S3Service s3Service;
    @Autowired
    private ImageService imageService;
    @Autowired
    private AuthService authService;

    @Value("${img.prefix.client.profile}")
    private String prefix;
    @Value("${img.profile.size}")
    private int size;

    public Cliente findById(Integer id) {
        UserSpringSecurity loggedUser = UserService.authenticated();
        if(loggedUser == null || !loggedUser.hasRole(Perfil.ADMIN) && !id.equals(loggedUser.getId())) {
            throw new AuthorizationException("Acesso negado");
        }

        Optional<Cliente> obj = repository.findById(id);
        return  obj.orElseThrow(() -> { throw new ObjectNotFoundException("Nenhum objeto foi encontrado com o ID: " + id); });
    }

    @Transactional
    public Cliente insert(Cliente obj) {
        enderecoRepository.saveAll(obj.getEnderecos());
        return  repository.save(obj);
    }

    public void update(Cliente obj, String email) {
        Cliente newObj = findByEmail(email);
        updateData(newObj, obj);
        repository.save(newObj);
    }

    public void delete(Integer id) {
        findById(id);
        try {
            repository.deleteById(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("O Cliente não pode ser excluído porque possuí pedidos associados a ele");
        }
    }

    public List<Cliente> findAll() {
        return repository.findAll();
    }

    public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repository.findAll(pageRequest);
    }

    public Cliente findByEmail(String email) {
        UserSpringSecurity loggedUser = UserService.authenticated();
        if(loggedUser == null || !loggedUser.hasRole(Perfil.ADMIN) && !email.equals(loggedUser.getUsername())) {
            throw new AuthorizationException("Acesso negado");
        }

        Cliente obj = repository.findByEmail(email);
        if(obj == null) {
            throw  new ObjectNotFoundException("Objeto não encontrado! id: " + loggedUser.getId() + ", Tipo: " + Cliente.class.getName());
        }

        return obj;
    }

    public Cliente fromDto(ClienteUpdateDTO objDto) {
        Cliente cliente = new Cliente(null, objDto.getNome(), objDto.getEmail(), null, null, null);

        addTelefone(cliente, objDto.getTelefone1(), objDto.getTelefone2(), objDto.getTelefone3());

        return cliente;
    }

    public Cliente fromDto(ClienteNewDTO objDto) {
        String senha = authService.sendPassToNewClient(objDto.getEmail());

        Cliente cliente = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()), bCryptPasswordEncoder.encode(senha));
        Cidade cidade = new Cidade(objDto.getCidadeId(), null, null);

        Endereco endereco = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cliente, cidade);
        cliente.getEnderecos().add(endereco);

        addTelefone(cliente, objDto.getTelefone1(), objDto.getTelefone2(), objDto.getTelefone3());

        return cliente;
    }

    public void up(String email) {
        Cliente cliente = repository.findByEmail(email);
        cliente.addPerfil(Perfil.ADMIN);

        repository.save(cliente);
    }

    public void down(String email) {
        Cliente cliente = repository.findByEmail(email);
        Set<Integer> perfisList = cliente.getPerfisList().stream().filter(perfil -> perfil != Perfil.ADMIN.getCod()).collect(Collectors.toSet());
        cliente.setPerfisList(perfisList);

        repository.save(cliente);
    }

    public URI uploadProfilePicture(MultipartFile multipartFile) {
        UserSpringSecurity loggedUser = UserService.authenticated();
        if (loggedUser == null) {
            throw new AuthorizationException("Acesso negado");
        }

        BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
        jpgImage = imageService.cropSquare(jpgImage);
        jpgImage = imageService.resize(jpgImage, size);
        String fileName = prefix + loggedUser.getId() + ".jpg";

        return s3Service.uplodaFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");
    }

    private void updateData(Cliente newObj, Cliente obj) {
        newObj.setNome(obj.getNome());
        newObj.setEmail(obj.getEmail());
        newObj.setTelefones(obj.getTelefones());
    }

    private void addTelefone(Cliente cliente, String telefone1, String telefone2, String telefone3) {
        cliente.getTelefones().add(telefone1);

        if(telefone2 != null ) {
            cliente.getTelefones().add(telefone2);
        }
        if(telefone3 != null ) {
            cliente.getTelefones().add(telefone3);
        }
    }
}
