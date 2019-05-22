package hello;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public List<IpAddress> getAllIps() {
        List<IpAddress> ips = new ArrayList<IpAddress>();
        addressRepository.findAll().forEach(ipAddress -> ips.add(ipAddress));
        return ips;
    }

    public IpAddress getAddressById(String ip) {
    	try{
    		return addressRepository.findById(ip).get();
    	}
    	catch(NoSuchElementException nse){}
    	
    	return null;
    }

    public void saveOrUpdate(IpAddress ip) {
    	addressRepository.save(ip);
    }
    
    public IpAddress acquire(String ip) {
    	IpAddress addr = getAddressById(ip);
    	if(addr != null && addr.getStatus().equals("available")){
    		addr.setStatus("acquired");
    		addressRepository.save(addr);
    		return addr;
    	}
    	return null;
    }
    
    public IpAddress release(String ip) {
    	IpAddress addr = getAddressById(ip);
    	if(addr != null && addr.getStatus().equals("acquired")){
    		addr.setStatus("available");
    		addressRepository.save(addr);
    		return addr;
    	}
    	return null;
    }

   
}
