insert into currency values (500169,str_to_date(substr("01-JAN-53 12.00.00.000000 AM",1,18), "%d-%M-%y %h.%i.%s"),'NA02','NA02',
str_to_date(substr("01-JUL-07 12.00.00.000000 AM",1,18),"%d-%M-%y %h.%i.%s"),null,null,'Ghanaian Cedi (to Jan 2008)',
0,null,2,0,str_to_date(substr("18-JUL-16 12.00.00.000000 AM",1,18),"%d-%M-%y %h.%i.%s"),'2016-07-18-0000.Full');

delete from currency where CurrencyId = 500169;

select * from currency;

LOAD DATA INFILE 'C:/Users/U6071369/Documents/MyJabberFiles/s.pang@thomsonreuters.com/currency.csv' INTO TABLE Currency character set latin1
  FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n'
  (CurrencyId,@EffectiveFrom,EffectiveFromPlus,EffectiveFromNACode,@EffectiveTo,EffectiveToMinus,EffectiveToNACode,UniqueName,IsSubunit,@SubunitFactor,DecimalPlaces,IsIsoHistorical,@SysFrom,SysFileId)
  SET EffectiveFrom = STR_TO_DATE(substr(@EffectiveFrom,1,18),'%d-%M-%y %h.%i.%s'),
    EffectiveTo = STR_TO_DATE(substr(@EffectiveTo,1,18),'%d-%M-%y %h.%i.%s'),
    SysFrom = STR_TO_DATE(substr(@SysFrom,1,18),'%d-%M-%y %h.%i.%s'),
    SubunitFactor = if(@SubunitFactor=null or @SubunitFactor='',0.0,@SubunitFactor);

SHOW VARIABLES LIKE "secure_file_priv";