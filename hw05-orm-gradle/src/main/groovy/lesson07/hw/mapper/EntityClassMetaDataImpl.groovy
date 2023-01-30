package lesson07.hw.mapper

import java.lang.reflect.Constructor
import java.lang.reflect.Field

class EntityClassMetaDataImpl implements EntityClassMetaData {

    @Override
    String getName() {

    }

    @Override
    Constructor<T> getConstructor() {

    }

    //Поле Id должно определять по наличию аннотации Id
    //Аннотацию @Id надо сделать самостоятельно
    @Override
    Field getIdField() {

    }

    @Override
    List<Field> getAllFields() {

    }

    List<Field> getFieldsWithoutId() {

    }
}
