package edu.miu.ea.commons.controller;

import edu.miu.ea.contracts.Code;
import edu.miu.ea.contracts.Response;
import edu.miu.ea.commons.exception.ResourceNotFoundException;
import edu.miu.ea.commons.service.BaseReadWriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;


/**
 * <h1>Maharishi University of Management<br/>Computer Science Department</h1>
 *
 * <p>Provides basic create/update/delete functionality over a resource (type T with an ID of type I)
 * and returns the response type of R. See {@link BaseReadWriteService} for more info.
 * This class extends the functionality of {@link BaseReadController}</p>
 *
 * @author Payman Salek
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public abstract class BaseReadWriteController<R, T, I> extends BaseReadController<R, T, I> {

    @Autowired
    private BaseReadWriteService<R, T, I> baseService;

    @PutMapping("/{id}")
    public R update(@PathVariable I id, @RequestBody R request) throws ResourceNotFoundException {
        return baseService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public Response delete(@PathVariable I id) {
        try {
            baseService.delete(id);
            return new Response(Code.Success, "");
        }catch (RuntimeException runtimeException) {
            return new Response(Code.NotExist, "deleted failed");
        }
    }
}
