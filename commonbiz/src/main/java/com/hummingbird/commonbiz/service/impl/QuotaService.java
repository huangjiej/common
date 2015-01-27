/**
 * 
 */
package com.hummingbird.commonbiz.service.impl;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hummingbird.commonbiz.entity.LimitSetting;
import com.hummingbird.commonbiz.entity.MobileDayLimit;
import com.hummingbird.commonbiz.entity.MobileMonthLimit;
import com.hummingbird.commonbiz.exception.QuotaException;
import com.hummingbird.commonbiz.mapper.LimitSettingMapper;
import com.hummingbird.commonbiz.mapper.MobileDayLimitMapper;
import com.hummingbird.commonbiz.mapper.MobileMonthLimitMapper;
import com.hummingbird.commonbiz.service.IQuotaService;
import com.hummingbird.commonbiz.util.LimitSettingUtil;
import com.hummingbird.commonbiz.vo.Quotable;

/**
 * 限额service
 * @author huangjiej_2
 * 2014年10月3日 下午9:04:17
 */
@Service
public class QuotaService implements IQuotaService<Quotable> {

	protected  final Log log = LogFactory.getLog(getClass());
	@Autowired
	private LimitSettingMapper limitmapper;
	@Autowired
	private MobileDayLimitMapper daylimitmapper;
	@Autowired
	private MobileMonthLimitMapper monthlimitmapper;
	
	/* (non-Javadoc)
	 * @see com.pay2b.service.IQuotaService#validateQuota(com.pay2b.vo.CreateOrderVo)
	 */
	@Override
	public  boolean validateQuota(Quotable createOrderVo)
			throws QuotaException {
		
		
		LimitSetting ls = getLimitSetting(createOrderVo);
		if(ls==null){
			if(log.isDebugEnabled()){
				log.debug("查询不到限额设置");
			}
			return false;
		}
		boolean checklimit=true;
		boolean adddaylimitsuccess = false;
		do{
			
			MobileDayLimit dayLimit = getDayLimit(createOrderVo);
			if(dayLimit.getUsed()>=ls.getMobileDayLimit()){
				if(log.isDebugEnabled()){
					log.debug(createOrderVo.getBuyerId()+"于sellerid["+createOrderVo.getSellerId()+"]日限额已超限："+dayLimit.getUsed());
				}
				return false;
			}
			MobileMonthLimit monthLimit = getMonthLimit(createOrderVo);
			if(monthLimit.getUsed()>=ls.getMobileMonthLimit()){
				if(log.isDebugEnabled()){
					log.debug(createOrderVo.getBuyerId()+"于sellerid["+createOrderVo.getSellerId()+"]月限额已超限："+monthLimit.getUsed());
				}
				return false;
			}
			
			
			
//			if(!adddaylimitsuccess){
//				不重复添加
				//限额+1
				int updatecount = daylimitmapper.addQuota(dayLimit);
				if(updatecount==0){
					//更新不成功
					continue;
				}
//				adddaylimitsuccess=true;
//			}
			int updatemcount = monthlimitmapper.addQuota(monthLimit);
			if(updatemcount==0){
				//更新不成功
				//还原日限额数据
				daylimitmapper.minuslimit(dayLimit);
				continue;
			}
			
			
			checklimit=false;
		}
		while(checklimit);
		return true;
		
//		QuotaLimitInMemory qlm = QuotaLimitInMemory.getInstance();
//		if(qlm.isOverLimit(createOrderVo){
//			return false;
//		}
//		
//		List<IQuotaVO> quotaVOs = getQuotaVOs(createOrderVo);
//		if(quotaVOs!=null){
//			for (Iterator iterator = quotaVOs.iterator(); iterator.hasNext();) {
//				IQuotaVO iQuotaVO = (IQuotaVO) iterator.next();
//				if(!iQuotaVO.validateQuota(createOrderVo)){
//					return false;
//				}
//			}
//		}
//		return true;
	}
	
	public MobileDayLimit getDayLimit(Quotable quotable){
		MobileDayLimit limit = new MobileDayLimit();
		limit.setBuyerId(quotable.getBuyerId());
		limit.setSellerId(quotable.getSellerId());
		limit.setDay(quotable.getCreateTime());
		MobileDayLimit dayLimit = daylimitmapper.selectByQuota(limit);
		if(dayLimit==null)
		{
			dayLimit = createDayLimit(quotable);
		}
		return dayLimit;
	}
	public MobileMonthLimit getMonthLimit(Quotable quotable){
		MobileMonthLimit limit = new MobileMonthLimit();
		limit.setBuyerId(quotable.getBuyerId());
		limit.setSellerId(quotable.getSellerId());
		limit.setMonth(quotable.getCreateTime());
		MobileMonthLimit monthLimit = monthlimitmapper.selectByQuota(limit);
		if(monthLimit==null)
		{
			monthLimit = createMonthLimit(quotable);
		}
		return monthLimit;
	}
	
	private MobileMonthLimit createMonthLimit(Quotable quotable) {
		LimitSetting ls = getLimitSetting(quotable);
		MobileMonthLimit limit = new MobileMonthLimit();
		limit.setBuyerId(quotable.getBuyerId());
		limit.setMonth(new Date());
		limit.setLocker(0);
		limit.setSellerId(quotable.getSellerId());
		limit.setUsed(0);		
		monthlimitmapper.insertSelective(limit);
		return limit;
	}

	public LimitSetting getLimitSetting(Quotable quotable){
		
		LimitSetting ls =LimitSettingUtil.getLimitSetting(quotable);
		return ls;
	}
	
	
	
	
	public MobileDayLimit createDayLimit(Quotable quotable){
		LimitSetting ls = getLimitSetting(quotable);
		Integer mobileDayLimit = ls.getMobileDayLimit();
		MobileDayLimit limit = new MobileDayLimit();
		limit.setBuyerId(quotable.getBuyerId());
		limit.setDay(new Date());
		limit.setLocker(0);
		limit.setSellerId(quotable.getSellerId());
		limit.setUsed(0);
		daylimitmapper.insertSelective(limit);
		return limit;
	}
	
//	public List<IQuotaVO> getQuotaVOs(CreateOrderVo quotable) 
//	{
//		//根据订单信息获取限额
//		List<IQuotaVO> quotas = new ArrayList<IQuotaVO>();
//		
//		LimitSetting limitsetting = limitmapper.selectByApp(quotable.getAppId());
//		
//		
//		
//		
//		quotas.add(new ProductQuota());
//		quotas.add(new SellerQuota());
//		quotas.add(new BuyerQuota());
//		return quotas;
//	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
