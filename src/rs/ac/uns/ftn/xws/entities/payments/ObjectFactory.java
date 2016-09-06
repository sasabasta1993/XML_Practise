package rs.ac.uns.ftn.xws.entities.payments;

import javax.xml.bind.annotation.XmlRegistry;


@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: rs.ac.uns.ftn.xws.entities.payments
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Invoice }
     * 
     */
    public Invoice createInvoice() {
        return new Invoice();
    }

    /**
     * Create an instance of {@link InvoiceItem }
     * 
     */
    public InvoiceItem createInvoiceItem() {
        return new InvoiceItem();
    }

    /**
     * Create an instance of {@link Invoice.InvoiceItems }
     * 
     */
    public Invoice.InvoiceItems createInvoiceInvoiceItems() {
        return new Invoice.InvoiceItems();
    }

}
