package edu.miu.ea.commons.service.mapper;

import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import org.apache.commons.lang.SerializationUtils;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * <h1>Maharishi University of Management<br/>Computer Science Department</h1>
 *
 * <p>This base class configures the Orika mapping framework for mapping different types.
 * By default, it maps all the attributes of the source class that have the same name as the target class
 * and leaves everything else untouched. But this default behavior can be modified
 * by overriding the registerMapper(...) method.</p>
 *
 * @author Payman Salek
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public abstract class BaseMapper<Source, Target extends Serializable> {

    private BoundMapperFacade<Source, Target> mapper;

    private Class<Source> sourceType;

    private Class<Target> targetType;

    public BaseMapper(MapperFactory mapperFactory, Class<Source> sourceType, Class<Target> targetType) {
        super();

        this.sourceType = sourceType;
        this.targetType = targetType;

        mapper = registerMapper(mapperFactory);
    }


    protected BoundMapperFacade<Source, Target> registerMapper(MapperFactory mapperFactory) {
        mapperFactory.classMap(sourceType, targetType)
                .byDefault() // copies all the fields that have the same name and ignores the rest
                .register();

        return mapperFactory.getMapperFacade(sourceType, targetType);
    }

    public boolean isUpdate(Source source, Target target) {
        if (null == source) {
            return false;
        }

        @SuppressWarnings("unchecked")
        Target clone = (Target) SerializationUtils.clone(target);

        return mapper.map(source, clone).equals(target) ? false : true;
    }

    public Target map(Source source) {
        return mapper.map(source);
    }

    public Target map(Source source, Target target) {
        return mapper.map(source, target);
    }

}
