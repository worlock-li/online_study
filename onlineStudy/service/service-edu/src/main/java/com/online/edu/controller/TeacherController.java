package com.online.edu.controller;
import com.online.edu.entity.Teacher;
import com.online.edu.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
@Api("讲师管理")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    /**
     * 查询所有
     * @return
     */
    @ApiOperation("查询所有讲师列表")
    @GetMapping("/findAll")
    public List<Teacher> findAll() {
        // 调用service查询所有
        return teacherService.list(null);
    }

    /**
     * 删除
     * @PathVariable /deleteById/{id}
     * 得到路径当中的id，id需要路径传递 /deleteById/123
     * @return
     */
    @ApiOperation("根据id删除讲师")
    @DeleteMapping("/deleteById/{id}")
    public Boolean deleteById(
            @ApiParam(name = "id", value = "讲师Id", required = true)
            @PathVariable String id) {
        // 调用service查询所有
        return teacherService.removeById(id);
    }


}

