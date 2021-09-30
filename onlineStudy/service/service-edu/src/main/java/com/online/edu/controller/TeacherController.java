package com.online.edu.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.online.commonutils.Result;
import com.online.edu.entity.Teacher;
import com.online.edu.entity.vo.TeacherQuery;
import com.online.edu.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author worlock
 * @since 2021-09-20
 */
@RestController
@RequestMapping("/edu/teacher")
@CrossOrigin
@Api("讲师管理")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("list/{pageNum}/{pageSize}")
    public Result list(@PathVariable long pageNum, @PathVariable long pageSize) {
        Page<Teacher> page = new Page<Teacher>(pageNum, pageSize);
        teacherService.page(page, null);
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        return Result.ok().data(map);
        // return Result.ok().data("total", page.getTotal()).data("list", page.getRecords());
    }

    /**
     * 带条件的分页查询
     * @param pageNum
     * @param pageSize
     * @param teacherQuery
     * @return
     */
    @GetMapping("queryList/{pageNum}/{pageSize}")
    public Result list(
            TeacherQuery teacherQuery,
            @PathVariable long pageNum,
            @PathVariable long pageSize) {
        Page<Teacher> page = new Page<>(pageNum, pageSize);

        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        // 添加条件
        if (!StringUtils.isEmpty(name)) {
            queryWrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level)) {
            queryWrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            queryWrapper.ge("create_time", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            queryWrapper.le("create_time", end);
        }

        teacherService.page(page, queryWrapper);
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        return Result.ok().data(map);
    }

    /**
     * 查询所有
     * @return
     */
    @ApiOperation("查询所有讲师列表")
    @GetMapping("/findAll")
    public Result findAll() {
        // 调用service查询所有
        List<Teacher> list = teacherService.list(null);
        return Result.ok().data("items", list);
    }

    /**
     * 删除
     * @PathVariable /deleteById/{id}
     * 得到路径当中的id，id需要路径传递 /deleteById/123
     * @return
     */
    @ApiOperation("根据id删除讲师")
    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(
            @ApiParam(name = "id", value = "讲师Id", required = true)
            @PathVariable String id) {
        // 调用service查询所有
        boolean remove = teacherService.removeById(id);
        return remove?Result.ok():Result.error();
    }

    /**
     * 添加讲师
     * @return
     */
    @ApiOperation("添加讲师")
    @PostMapping("/save")
    public Result deleteById(@RequestBody Teacher teacher) {
        boolean isSave = teacherService.save(teacher);
        if (isSave) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }

    /**
     * 根据id查询
     * @return
     */
    @ApiOperation("根据id查询")
    @GetMapping("/getById/{id}")
    public Result getById(@PathVariable String id) {
        Teacher teacher = teacherService.getById(id);
        return Result.ok().data("teacher", teacher);
    }

    /**
     * 修改
     * @return
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    public Result update(@RequestBody Teacher teacher) {
        boolean isSuccess = teacherService.updateById(teacher);
        if (isSuccess) {
            return Result.ok().message("操作成功！");
        } else {
            return Result.error().message("操作失败！");
        }
    }


}

