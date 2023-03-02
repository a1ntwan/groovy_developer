package ru.otus.hw.service


import org.slf4j.Logger
import org.slf4j.LoggerFactory
import ru.otus.hw.model.Client
import ru.otus.hw.repository.DataTemplate
import ru.otus.hw.sessionmanager.TransactionRunner

import java.sql.ResultSet


class DbServiceClientImpl implements DBServiceClient {
    private static final Logger log = LoggerFactory.getLogger(DbServiceClientImpl.class)

    private final DataTemplate<Client> dataTemplate
    private final TransactionRunner transactionRunner

    DbServiceClientImpl(TransactionRunner transactionRunner, DataTemplate<Client> dataTemplate) {
        this.transactionRunner = transactionRunner
        this.dataTemplate = dataTemplate
    }

    @Override
    Client saveClient(Client client) {
        transactionRunner.doInTransaction(connection -> {
            if (client.getId() == null) {
                def clientId = dataTemplate.insert(connection, client)
                def createdClient = new Client(clientId, client.getName())
                log.info("created client: {}", createdClient)
                createdClient
            } else {
                dataTemplate.update(connection, client)
                log.info("updated client: {}", client)
                client
            }
        });
    }

    @Override
    Optional<Client> getClient(long id) {
        transactionRunner.doInTransaction(connection -> {
            ResultSet rs = dataTemplate.findById(connection, id)
            Client client = new Client()
            while (rs.next()) {
                client.id = rs.getInt('id')
                client.name = rs.getString('name')
            }
            log.info("client: {}", client.name)
            return Optional.of(client)
        });
    }

    @Override
    List<Client> findAll() {
        transactionRunner.doInTransaction(connection -> {
            ArrayList<Client> lst = new ArrayList()
            ResultSet rs = dataTemplate.findAll(connection)
            while (rs.next()) {
                Client client = new Client()
                client.id = rs.getInt('id')
                client.name = rs.getString('name')
                log.info("clientList:{}", client.name)
                lst << client
            }
            return lst
        });
    }
}