CREATE TABLE customer(
id VARCHAR(50),
name VARCHAR(100)
);

CREATE TABLE itemCategory(
id VARCHAR(50),
name VARCHAR(100),
description VARCHAR(100)
);

CREATE TABLE productGroup(
id VARCHAR(50),
description VARCHAR(100),
itemCategory_id VARCHAR(50)
);

CREATE TABLE item(
id VARCHAR(50),
name VARCHAR(100),
description VARCHAR(100),
productGroup_id VARCHAR(50),
itemCategory_id VARCHAR(50),
purchasePrice FLOAT,
salesPrice FLOAT
);

CREATE TABLE vendor(
id VARCHAR(50),
name VARCHAR(100)
);

CREATE TABLE purchaseOrder(
id VARCHAR(30),
number VARCHAR(50),
created DATE
);

CREATE TABLE salesOrder(
id VARCHAR(50),
created DATE,
customer_id VARCHAR(50)
);

CREATE TABLE purchaseOrderLine(
id VARCHAR(50),
lineNumber INT,
item_id VARCHAR(50),
quantity INT,
price FLOAT,
ammount INT
);

CREATE TABLE salesOrderLine(
id VARCHAR(50),
lineNumber INT,
item_id VARCHAR(50),
quantity INT,
price FLOAT,
ammount INT
);

CREATE TABLE postedPurchaseOrder(
id VARCHAR(30),
number VARCHAR(50),
created DATE
);

CREATE TABLE postedSalesOrder(
id VARCHAR(50),
created DATE,
customer_id VARCHAR(50)
);

CREATE TABLE postedPurchaseOrderLine(
id VARCHAR(50),
lineNumber INT,
item_id VARCHAR(50),
quantity INT,
price FLOAT,
ammount INT
);

CREATE TABLE postedSalesOrderLine(
id VARCHAR(50),
lineNumber INT,
item_id VARCHAR(50),
quantity INT,
price FLOAT,
ammount INT
);