package hello;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.util.SubnetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IpAddressController {
	
	@Autowired
    private AddressService addressService;
	
	@GetMapping("/addresses")
    private List<IpAddress> getAllAddresses() {
        return addressService.getAllIps();
    }
	
	@PostMapping("/addresses")
    private String saveCidrBlock(@RequestBody String cidr) {
		SubnetUtils subnetUtils = new SubnetUtils(cidr);
        subnetUtils.setInclusiveHostCount(true);
        SubnetUtils.SubnetInfo info = subnetUtils.getInfo();
        
        String[] addresses = info.getAllAddresses();
        List<IpAddress> newList = new ArrayList<IpAddress>();
        for(String add: addresses){
        	//Create address object and set status to available
        	//if IP DOES NOT exist, create ip object and store in list
        	IpAddress ip = new IpAddress(add, "available");
        	addressService.saveOrUpdate(ip);
        	//newList.add(ip);
        }
        return cidr;
    }


 
   /* @RequestMapping("/list")  10.12.0.1/24"  192.168.0.1/32
    public List<IpAddress> address(@RequestParam(value="ip") String ip) {
    	List<IpAddress> ipList = new ArrayList<IpAddress>();
        IpAddress a = new IpAddress("localhost",String.format(template, ip));
        IpAddress b = new IpAddress("127.0.0.1",String.format(template, ip));
        ipList.add(a);
        ipList.add(b);
        return ipList;
    }*/
	
	/*@GetMapping("/addresses/{ip}")
    private IpAddress acquire(@PathVariable("ip") String ip) {
        return addressService.acquire(ip);
    }
    
    @DeleteMapping("/addresses/{ip}")
    private IpAddress release(@PathVariable("ip") String ip) {
    	return addressService.release(ip);
    }
    */
	
	@GetMapping("/addresses/{ip}")
    private ResponseEntity<IpAddress> acquire(@PathVariable("ip") String ip) {
		IpAddress addr = addressService.acquire(ip);
		if(addr == null){
			return new ResponseEntity<>(addr, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(addr, HttpStatus.OK);
    }

    @DeleteMapping("/addresses/{ip}")
    private ResponseEntity<IpAddress> release(@PathVariable("ip") String ip) {
    	IpAddress addr = addressService.release(ip);
		if(addr == null){
			return new ResponseEntity<>(addr, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(addr, HttpStatus.OK);
    }

    
}
