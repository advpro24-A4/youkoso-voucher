ALTER TABLE voucher
DROP
COLUMN discount_percentage;

ALTER TABLE voucher
DROP
COLUMN minimum_order;

ALTER TABLE voucher
    ADD discount_percentage INTEGER NOT NULL;

ALTER TABLE voucher
    ADD minimum_order INTEGER DEFAULT 0;