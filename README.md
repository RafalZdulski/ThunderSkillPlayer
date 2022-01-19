purpose of this program is to fetch data from
[thunderskill](https://thunderskill.com/en)
and insert to MongoDB database

first argument is mandatory and it is nickname from the game

optional parameters:

**--dburi-%uri%**  <br/>
where _%uri%_ is MongoDB URI e.g. _"mongodb://localhost:27017"_ <br>
[if not stated will use _mongodb://localhost:27017_]

**--dbname-%name%** <br/>
where _%name%_ id MongoDB database name <br>
[if not stated will use _tf_thunderskill_players_ ]

**--update** <br/>
pushes "updateButton" before fetching data