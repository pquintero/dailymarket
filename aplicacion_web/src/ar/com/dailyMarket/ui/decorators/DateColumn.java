package ar.com.dailyMarket.ui.decorators;

import java.util.Date;

public class DateColumn implements Comparable {
    private Date date;
    private String str;
    
    public DateColumn(Date date, String str) {
        this.date = date;
        this.str = str;
    }

    public Date getDate() {
        return this.date;
    }

    public String toString() {
        return str;
    }

    public int compareTo(Object o) {
        try {
    	if (date == null || !(o instanceof DateColumn)) {
            return -1;
        }
        if (o == null || ((DateColumn) o).getDate() == null) {
            return 1;
        }
        return this.date.compareTo(((DateColumn) o).getDate());
        } catch (Exception e) {
        	o.toString(); //TODO al editar fecha de tarea y grabar y volver al listado, castea mal
        	date.toString();
        	return 0;
        }
    }
}
