package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.OfferRepository;
import domain.Offer;

@Component
@Transactional
public class StringToOfferConverter implements Converter<String, Offer>{
	
	@Autowired
	private OfferRepository offerRepository;
	
	@Override
	public Offer convert(String text) {
		Offer result;
		int id;
		
		try {
			if(StringUtils.isEmpty(text)) {
				result = null;
			} else {
				id = Integer.valueOf(text);
				result = offerRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}