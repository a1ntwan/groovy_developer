package ru.otus.hw.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import ru.otus.hw.model.Manager
import ru.otus.hw.repository.DataTemplate
import ru.otus.hw.sessionmanager.TransactionRunner

import java.sql.ResultSet

class DbServiceManagerImpl implements DBServiceManager {
    private static final Logger log = LoggerFactory.getLogger(DbServiceManagerImpl.class)

    private final DataTemplate<Manager> managerDataTemplate
    private final TransactionRunner transactionRunner

    public DbServiceManagerImpl(TransactionRunner transactionRunner, DataTemplate managerDataTemplate) {
        this.transactionRunner = transactionRunner
        this.managerDataTemplate = managerDataTemplate
    }

    @Override
    Manager saveManager(Manager manager) {
        transactionRunner.doInTransaction(connection -> {
            if (manager.getNo() == null) {
                def managerNo = managerDataTemplate.insert(connection, manager)
                def createdManager = new Manager(managerNo, manager.getLabel(), manager.getParam1())
                log.info("created manager: {}", createdManager)
                createdManager
            } else {
                managerDataTemplate.update(connection, manager)
                log.info("updated manager: {}", manager)
                manager;
            }
        });
    }

    @Override
    Optional<Manager> getManager(long no) {
        transactionRunner.doInTransaction(connection -> {
            ResultSet rs = managerDataTemplate.findById(connection, no)
            Manager manager = new Manager()
            while (rs.next()) {
                manager.no = rs.getInt('no')
                manager.label = rs.getString('label')
                manager.param1 = rs.getString('param1')
            }
            log.info("manager: {}", manager.label)
            return Optional.of(manager)
        });
    }

    @Override
    List<Manager> findAll() {
        transactionRunner.doInTransaction(connection -> {
            ArrayList<Manager> lst = new ArrayList()
            ResultSet rs = managerDataTemplate.findAll(connection)
            while (rs.next()) {
                Manager manager = new Manager()
                manager.no = rs.getInt('no')
                manager.label = rs.getString('label')
                manager.param1 = rs.getString('param1')
                log.info("clientList:{}", manager.label)
                lst << manager
            }
            return lst
        });
    }
}
