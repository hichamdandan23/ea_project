package edu.miu.ea.commons.controller;

import java.util.List;

import edu.miu.ea.commons.exception.ResourceNotFoundException;
import edu.miu.ea.commons.service.BaseReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <h1>Maharishi University of Management<br/>Computer Science Department</h1>
 *
 * <p>Provides basic read and search capabilities over a resource (type T with an ID of type I)
 * and returns the response type of R. See {@link BaseReadService} for more info.</p>
 *
 * @author Payman Salek
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public abstract class BaseReadController<R, T, I> {

    @Autowired
    private BaseReadService<R, T, I> baseService;

    @GetMapping("/{id}")
    public R findById(@PathVariable I id) throws ResourceNotFoundException {
        return baseService.findById(id);
    }

    @GetMapping
    public Page<R> getPage(Pageable pageable) {
        return baseService.findAll(pageable);
    }

    @GetMapping(params = "fetch-all=true")
    public List<R> getAll() {
        return baseService.findAll();
    }

    @GetMapping("/count")
    public Long findAllCount() {
        return baseService.findAllCount();
    }

    @GetMapping("/search/count")
    public Long searchCount(@RequestParam(value = "query") String query) {
        return baseService.searchCount(query);
    }

    @GetMapping("/search")
    public Page<R> search(@RequestParam(value = "query") String query, Pageable pageable) {
        return baseService.search(query, pageable);
    }

    @GetMapping(value = "/search", params = "fetch-all=true")
    public List<R> search(@RequestParam(value = "query") String query) {
        return baseService.search(query);
    }

}
