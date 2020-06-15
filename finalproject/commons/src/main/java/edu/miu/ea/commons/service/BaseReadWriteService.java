package edu.miu.ea.commons.service;


import edu.miu.ea.commons.exception.ResourceNotFoundException;

/**
 * <h1>Maharishi University of Management<br/>Computer Science Department</h1>
 *
 * <p>Provides basic crteate/update/delete capabilities on top of read and search capabilities
 * already provided by {@link BaseReadService}.</p>
 *
 * @author Payman Salek
 * @version 1.0.0
 * @since 1.0.0
 */
public interface BaseReadWriteService<R, T, I> extends BaseReadService<R, T, I> {

    R update(I id, R request) throws ResourceNotFoundException;
    void delete(I id);
}
