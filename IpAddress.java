package hello;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class IpAddress {
	@Id
	private  String ip;
    private  String status;

    public IpAddress() {
        this.ip = "";
        this.status = "";
    }
    public IpAddress(String ip, String status) {
        this.ip = ip;
        this.status = status;
    }

    public String getIp() {
        return ip;
    }

    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
}
