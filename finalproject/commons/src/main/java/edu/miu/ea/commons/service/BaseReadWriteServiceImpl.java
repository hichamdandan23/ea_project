package edu.miu.ea.commons.service;

import edu.miu.ea.commons.exception.ResourceNotFoundException;
import edu.miu.ea.commons.service.mapper.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;


/**
 * <h1>Maharishi University of Management<br/>Computer Science Department</h1>
 *
 * <p>See {@link BaseReadWriteService}.</p>
 *
 * @author Payman Salek
 * @version 1.0.0
 * @since 1.0.0
 */
@Transactional
public abstract class BaseReadWriteServiceImpl<R extends Serializable, T extends Serializable, I> extends BaseReadServiceImpl<R, T, I> implements BaseReadWriteService<R, T, I> {

    @Autowired
    private BaseMapper<R, T> requestMapper;

    @Override
    public R update(I id, R request) throws ResourceNotFoundException {
        T entity = baseRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        entity = requestMapper.map(request, entity);
        return convertEntityToResponse(baseRepository.save(entity));
    }

    public void delete(I id) {
        baseRepository.deleteById(id);
    }
}
