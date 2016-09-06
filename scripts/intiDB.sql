DELETE FROM `xws`.`Invoice`;
DELETE FROM `xws`.`InvoiceItem`;

INSERT INTO `xws`.`Invoice` (`acountNumber`, `buyerAddress`, `buyerName`, `currency`, `currencyDate`, `date`, `deleted`, `suplierAddress`, `suplierName`, `supplierPib`, `totalAmount`, `totalGoodsValue`, `totalRabate`, `totalServiceValue`, `totalTax`, `totalValue`, `version`) VALUES ('1226458', 'Skojevska 12', 'Home Brew INC', 'rsd', '7.1.2015.', '7.1.2015.', '0', 'Zorza Klemansoa 18', 'Majstor za pivo', '1232233', '5000', '5000', '0', '0', '20', '5000', '0');

INSERT INTO `xws`.`InvoiceItem` (`amount`, `deleted`, `goodsName`, `measureUnit`, `minusRabat`, `orderNumber`, `pricePerUnit`, `quantity`, `rabateAmount`, `rabatePercentage`, `totalTax`, `version`, `invoiceId`) VALUES ('3000', '0', 'Hmelj', 'kilogram', '0', '1', '1000', '3', '0', '0', '20', '0', '1');
INSERT INTO `xws`.`InvoiceItem` (`amount`, `deleted`, `goodsName`, `measureUnit`, `minusRabat`, `orderNumber`, `pricePerUnit`, `quantity`, `rabateAmount`, `rabatePercentage`, `totalTax`, `version`, `invoiceId`) VALUES ('2000', '0', 'Jecam', 'kilogram', '0', '2', '1000', '2', '0', '0', '20', '0', '1');
