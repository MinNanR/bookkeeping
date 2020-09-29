package site.minnan.bookkeeping.userinterface.facade;

import cn.hutool.core.map.MapBuilder;
import cn.hutool.core.map.MapUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.minnan.bookkeeping.infrastructure.annocation.Operation;
import site.minnan.bookkeeping.userinterface.response.ResponseEntity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("management/common")
public class CommonController {

    /**
     * 日志操作类型下拉框
     *
     * @return
     */
    @PostMapping("getLogOperation")
    public ResponseEntity<Map<Object, Object>> getLogOperation() {
        Operation[] operations = Operation.values();
        List<String> operationStringList =
                Arrays.stream(operations).map(Operation::operationName).collect(Collectors.toList());
        return ResponseEntity.success(MapBuilder.create().put("list", operationStringList).build());
    }
}
