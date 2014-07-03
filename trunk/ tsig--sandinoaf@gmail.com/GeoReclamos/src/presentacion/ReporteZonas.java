package presentacion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;

import org.primefaces.context.RequestContext;

import com.controllers.IZonaHndlr;
import com.controllers.ZonaHndlr;
import com.utilities.reporZona;

@ManagedBean
public class ReporteZonas implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ReporteZonas (){}

	private Date start;
	private Date end;
	private List<reporZona> records;
		
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}
	public List<reporZona> getRecords() {
		return records;
	}
	public void setRecords(List<reporZona> records) {
		this.records = records;
	}
	

	public void consultar(){
		RequestContext requestContext = RequestContext.getCurrentInstance();
        
        requestContext.update("form:display");
        requestContext.execute("PF('dlg').show()");
        
		IZonaHndlr zH = new ZonaHndlr();
		List<reporZona> lst = new ArrayList<>();
		if (zH.reportPorzona(start, end) != null){
			setRecords(zH.reportPorzona(start, end));		
		}else{
			setRecords(lst);
		}		
	}
}
