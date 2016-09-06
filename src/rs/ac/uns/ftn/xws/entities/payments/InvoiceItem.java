package rs.ac.uns.ftn.xws.entities.payments;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "orderNumber",
    "goodsName",
    "quantity",
    "measureUnit",
    "pricePerUnit",
    "amount",
    "rebatePercentage",
    "rebateAmount",
    "minusRebate",
    "totalTax"
})
@XmlRootElement(name = "invoiceItem")
public class InvoiceItem extends Identifiable {

    protected int orderNumber;
    @XmlElement(required = true)
    protected String goodsName;
    protected double quantity;
    @XmlElement(required = true)
    protected String measureUnit;
    protected double pricePerUnit;
    protected double amount;
    protected double rebatePercentage;
    protected double rebateAmount;
    protected double minusRebate;
    protected double totalTax;
    @XmlAttribute(name = "id")
    protected Long id;

    /**
     * Gets the value of the orderNumber property.
     * 
     */
    public int getOrderNumber() {
        return orderNumber;
    }

    /**
     * Sets the value of the orderNumber property.
     * 
     */
    public void setOrderNumber(int value) {
        this.orderNumber = value;
    }

    /**
     * Gets the value of the goodsName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGoodsName() {
        return goodsName;
    }

    /**
     * Sets the value of the goodsName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGoodsName(String value) {
        this.goodsName = value;
    }

    /**
     * Gets the value of the quantity property.
     * 
     */
    public double getQuantity() {
        return quantity;
    }

    /**
     * Sets the value of the quantity property.
     * 
     */
    public void setQuantity(double value) {
        this.quantity = value;
    }

    /**
     * Gets the value of the measureUnit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMeasureUnit() {
        return measureUnit;
    }

    /**
     * Sets the value of the measureUnit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMeasureUnit(String value) {
        this.measureUnit = value;
    }

    /**
     * Gets the value of the pricePerUnit property.
     * 
     */
    public double getPricePerUnit() {
        return pricePerUnit;
    }

    /**
     * Sets the value of the pricePerUnit property.
     * 
     */
    public void setPricePerUnit(double value) {
        this.pricePerUnit = value;
    }

    /**
     * Gets the value of the amount property.
     * 
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Sets the value of the amount property.
     * 
     */
    public void setAmount(double value) {
        this.amount = value;
    }

    /**
     * Gets the value of the rebatePercentage property.
     * 
     */
    public double getRebatePercentage() {
        return rebatePercentage;
    }

    /**
     * Sets the value of the rebatePercentage property.
     * 
     */
    public void setRebatePercentage(double value) {
        this.rebatePercentage = value;
    }

    /**
     * Gets the value of the rebateAmount property.
     * 
     */
    public double getRebateAmount() {
        return rebateAmount;
    }

    /**
     * Sets the value of the rebateAmount property.
     * 
     */
    public void setRebateAmount(double value) {
        this.rebateAmount = value;
    }

    /**
     * Gets the value of the minusRebate property.
     * 
     */
    public double getMinusRebate() {
        return minusRebate;
    }

    /**
     * Sets the value of the minusRebate property.
     * 
     */
    public void setMinusRebate(double value) {
        this.minusRebate = value;
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

}
