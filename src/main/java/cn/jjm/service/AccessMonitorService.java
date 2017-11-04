package cn.jjm.service;

import cn.jjm.annotation.EnableMonitorConfiguration;
import cn.jjm.dao.AccessMonitor;
import cn.jjm.dao.AccessMonitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 保存数据到es
 * @author jiangjingming
 */
@ConditionalOnBean(annotation = EnableMonitorConfiguration.class)
@Service
public class AccessMonitorService {

    @Autowired
    private AccessMonitorRepository repository;

    public void saveAccessMonitor(AccessMonitor accessMonitor) {
        List<AccessMonitor> accessMonitors = findIsExistAndSave(accessMonitor);
        if (Objects.isNull(accessMonitor) || accessMonitors.size() < 1) {
            accessMonitor.setAccessTimes(1L);
            repository.save(accessMonitor);
        } else {
            AccessMonitor oldAccessMonitor = accessMonitors.get(0);
            oldAccessMonitor.setAccessTimes(oldAccessMonitor.getAccessTimes() + 1L);
            repository.save(oldAccessMonitor);
        }
    }

    /**
     * 判断是否存在
     *
     * @param accessMonitor
     * @return
     */
    public List<AccessMonitor> findIsExistAndSave(AccessMonitor accessMonitor) {
        return repository.findByMethodSignatureAndMethodNameAndAccessDate(accessMonitor.getMethodSignature(), accessMonitor.getMethodName(), accessMonitor.getAccessDate());

    }

    /**
     * 查询
     * @param methodName
     * @return
     */
    public List<AccessMonitor> findMethodName(String methodName) {
        return repository.findByMethodName(methodName);
    }

}