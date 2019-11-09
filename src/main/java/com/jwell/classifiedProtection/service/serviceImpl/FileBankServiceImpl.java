package com.jwell.classifiedProtection.service.serviceImpl;

import com.jwell.classifiedProtection.entry.FileBank;
import com.jwell.classifiedProtection.mapper.FileBankMapper;
import com.jwell.classifiedProtection.service.IFileBankService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 存储附件的数据表 服务实现类
 * </p>
 *
 * @author RonnieXu
 * @since 2019-10-15
 */
@Service
public class FileBankServiceImpl extends ServiceImpl<FileBankMapper, FileBank> implements IFileBankService {

}
