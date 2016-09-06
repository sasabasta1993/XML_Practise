package rs.ac.uns.ftn.xws.entities.payments;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "supplierName",
    "supplierAddress",
    "supplierTIN",
    "buyerName",
    "buyerAddress",
    "accountNumber",
    "date",
    "totalGoodsValue",
    "totalServiceValue",
    "totalValue",
    "totalRebate",
    "totalTax",
    "currency",
    "totalAmount",
    "currencyDate",
    "invoiceItems"
})
@XmlRootElement(name = "invoice")
public class Invoice extends Identifiable {

    @XmlElement(required = true)
    protected String supplierName;
    @XmlElement(required = true)
    protected String supplierAddress;
    @XmlElement(required = true)
    protected String supplierTIN;
    @XmlElement(required = true)
    protected String buyerName;
    @XmlElement(required = true)
    protected String buyerAddress;
    protected int accountNumber;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar date;
    protected double totalGoodsValue;
    protected double totalServiceValue;
    protected double totalValue;
    protected double totalRebate;
    protected double totalTax;
    @XmlElement(required = true)
    protected String currency;
    protected double totalAmount;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar currencyDate;
    @XmlElement(required = true)
    protected Invoice.InvoiceItems invoiceItems;
    @XmlAttribute(name = "id")
    protected Long id;

    /**
     * Gets the value of the supplierName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSupplierName() {
        return supplierName;
    }

    /**
     * Sets the value of the supplierName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSupplierName(String value) {
        this.supplierName = value;
    }

    /**
     * Gets the value of the supplierAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSupplierAddress() {
        return supplierAddress;
    }

    /**
     * Sets the value of the supplierAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSupplierAddress(String value) {
        this.supplierAddress = value;
    }

    /**
     * Gets the value of the supplierTIN property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSupplierTIN() {
        return supplierTIN;
    }

    /**
     * Sets the value of the supplierTIN property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSupplierTIN(String value) {
        this.supplierTIN = value;
    }

    /**
     * Gets the value of the buyerName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBuyerName() {
        return buyerName;
    }

    /**
     * Sets the value of the buyerName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBuyerName(String value) {
        this.buyerName = value;
    }

    /**
     * Gets the value of the buyerAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBuyerAddress() {
        return buyerAddress;
    }

    /**
     * Sets the value of the buyerAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBuyerAddress(String value) {
        this.buyerAddress = value;
    }

    /**
     * Gets the value of the accountNumber property.
     * 
     */
    public int getAccountNumber() {
        return accountNumber;
    }

    /**
     * Sets the value of the accountNumber property.
     * 
     */
    public void setAccountNumber(int value) {
        this.accountNumber = value;
    }

    /**
     * Gets the value of the date property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDate() {
        return date;
    }

    /**
     * Sets the value of the date property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDate(XMLGregorianCalendar value) {
        this.date = value;
    }

    /**
     * Gets the value of the totalGoodsValue property.
     * 
     */
    public double getTotalGoodsValue() {
        return totalGoodsValue;
    }

    /**
     * Sets the value of the totalGoodsValue property.
     * 
     */
    public void setTotalGoodsValue(double value) {
        this.totalGoodsValue = value;
    }

    /**
     * Gets the value of the totalServiceValue property.
     * 
     */
    public double getTotalServiceValue() {
        return totalServiceValue;
    }

    /**
     * Sets the value of the totalServiceValue property.
     * 
     */
    public void setTotalServiceValue(double value) {
        this.totalServiceValue = value;
    }

    /**
     * Gets the value of the totalValue property.
     * 
     */
    public double getTotalValue() {
        return totalValue;
    }

    /**
     * Sets the value of the totalValue property.
     * 
     */
    public void setTotalValue(double value) {
        this.totalValue = value;
    }

    /**
     * Gets the value of the totalRebate property.
     * 
     */
    public double getTotalRebate() {
        return totalRebate;
    }

    /**
     * Sets the value of the totalRebate property.
     * 
     */
    public void setTotalRebate(double value) {
        this.totalRebate = value;
    }

    /**
     * Gets the value of the totalTax property.
     * 
     */
    public double getTotalTax() {
        return totalTax;
    }

    /**
     * Sets the value of the totalTax property.
     * 
     */
    public void setTotalTax(double value) {
        this.totalTax = value;
    }

    /**
     * Gets the value of the currency property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Sets the value of the currency property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrency(String value) {
        this.currency = value;
    }

    /**
     * Gets the value of the totalAmount property.
     * 
     */
    public double getTotalAmount() {
        return totalAmount;
    }

    /**
     * Sets the value of the totalAmount property.
     * 
     */
    public void setTotalAmount(double value) {
        this.totalAmount = value;
    }

    /**
     * Gets the value of the currencyDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCurrencyDate() {
        return currencyDate;
    }

    /**
     * Sets the value of the currencyDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCurrencyDate(XMLGregorianCalendar value) {
        this.currencyDate = value;
    }

    /**
     * Gets the value of the invoiceItems property.
     * 
     * @return
     *     possible object is
     *     {@link Invoice.InvoiceItems }
     *     
     */
    public Invoice.InvoiceItems getInvoiceItems() {
        return invoiceItems;
    }

    /**
     * Sets the value of the invoiceItems property.
     * 
     * @param value
     *     allowed object is
     *     {@link Invoice.InvoiceItems }
     *     
     */
    public void setInvoiceItems(Invoice.InvoiceItems value) {
        this.invoiceItems = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setId(Long value) {
        this.id = value;
    }


    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "invoiceItem"
    })
    public static class InvoiceItems {

        @XmlElement(required = true)
        protected List<InvoiceItem> invoiceItem;

        /**
         * Gets the value of the invoiceItem property.
         * 
         * Objects of the following type(s) are allowed in the list
         * {@link InvoiceItem }
         * 
         * 
         */
        public List<InvoiceItem> getInvoiceItem() {
            if (invoiceItem == null) {
                invoiceItem = new ArrayList<InvoiceItem>();
            }
            return this.invoiceItem;
        }

    }

}
