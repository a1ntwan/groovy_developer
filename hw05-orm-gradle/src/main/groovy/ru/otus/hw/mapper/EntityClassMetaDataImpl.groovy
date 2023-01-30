package ru.otus.hw.mapper

import ru.otus.hw.annotation.Id
import ru.otus.hw.exception.EntityMetaDataException

import java.lang.reflect.Constructor
import java.lang.reflect.Field

class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {
    Class<T> clazz

    Field idField
    List<Field> withoutIdFiles = []
    Constructor<T> constructor

    EntityClassMetaDataImpl(Class<T> clazz) {
        this.clazz = clazz
        this.constructor = findFirstConstructorWithoutparameters()
        clazz.metaClass.getProperties().each {
            println(it.name)
        }
        clazz   .declaredFields.each {println(it.name)}
//        List<Field> fields = clazz.getProperties()['declaredFields']
//        fields.each {
//            if (it.getName() == findIdField().getName()){
//                this.idField = it
//            } else {
//                println(it)
//                this.withoutIdFiles << it
//            }
//        }

    }

    def findIdField() {
        for (def field in clazz.getDeclaredFields()) {
            if(field.isAnnotationPresent(Id.class)) {
                return field;
            }
        }
        throw new EntityMetaDataException("@id not found for class ${getName()}")
    }

    def findFirstConstructorWithoutparameters() {
        return this.constructor
    }

    @Override
    String getName() {
        return clazz.name
    }

    @Override
    List<Field> getAllFields() {
        return clazz.getDeclaredFields()
    }

    @Override
    List<Field> getFieldsWithoutId() {
        return withoutIdFiles
    }
}
