INSERT INTO category(name) 
VALUES ('Hard Drives'), ('Memory'), ('Flash Drives');

INSERT INTO product(name, description, price, category_id) 
VALUES ('Seagate', 'BarraCuda HDD 1TB 7200rpm 64MB ST1000DM010 3.5 SATA III', 1519.0, 1),  
      ('Western Digital', 'Purple 1TB 64MB 5400rpm WD10PURZ 3.5 SATA III', 1809.0, 1),  
      ('Samsung', '7200rpm 16MB (HD642JJ) Refurbished', 855.0, 1),  
      ('AMD', 'SO-DIMM 4GB AMD Radeon R5 Entertainment (R534G1601S1SL-UO)', 647.0, 2),  
      ('Crucial', 'SODIMM DDR4-3200 32768MB PC4-25600 (CT32G4SFD832A)', 4995.0, 2),  
      ('Hynix', 'SODIMM DDR2-800 2048MB (HYMP125S64CP8-S6)', 499.0, 2),  
      ('Transcend', 'JetFlash 700 32GB (TS32GJF700)', 249.0, 3),  
      ('SanDisk', 'Cruzer Force 32GB (SDCZ71-032G-B35)', 159.0, 3),  
      ('Verbatim', 'Metal Executive USB 2.0 64GB Silver (98750)', 521.0, 3);

