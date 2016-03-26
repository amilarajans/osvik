package com.origins.osvik.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.origins.osvik.domain.Client;
import com.origins.osvik.dto.ClientRepresentation;
import com.origins.osvik.dto.Page;
import com.origins.osvik.repository.ClientRepository;
import com.origins.osvik.web.rest.exception.ConflictException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Amila-Kumara on 3/12/2016.
 */
@RestController
@RequestMapping(value = "api/v1/client")
public class ClientResource {
    private final Logger log = LoggerFactory.getLogger(ClientResource.class);

    @Autowired
    private Environment env;
    @Qualifier("clientRepository")
    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping(value = {"/all"}, method = {RequestMethod.GET}, produces = {"application/json"})
    @Timed
    public List<Client> getAllByPage(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        return clientRepository.findAll(new PageRequest(page - 1, size, new Sort(Sort.Direction.ASC, "id"))).getContent();
    }

    @RequestMapping(value = {"/count"}, method = {RequestMethod.GET}, produces = {"application/json"})
    @Timed
    public Page getCount() {
        return new Page(clientRepository.count(), Long.parseLong(env.getProperty("result.page.size")));
    }

    @RequestMapping(value = {"/save"}, method = {RequestMethod.POST}, produces = {"application/json"})
    @Timed
    public void save(@RequestBody ClientRepresentation client) {
        if (clientRepository.findOneByCode(client.getCode()) == null) {
            Client newClient = new Client();
            newClient.setCode(client.getCode());
            newClient.setName(client.getName());
            newClient.setEmail(client.getEmail());
            newClient.setAddress(client.getAddress());
            newClient.setTel(client.getTel());
            newClient.setWeb(client.getWeb());
            newClient.setRemark(client.getRemark());
            clientRepository.save(newClient);
        } else {
            throw new ConflictException("Client already exist with code " + client.getCode());
        }
    }


}