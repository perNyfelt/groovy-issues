/* Alternative version using @Grab */
@Grab('com.h2database:h2:2.3.232')
import groovy.ant.AntBuilder

def project = new AntBuilder()

project.with {

  URL h2 = this.class.classLoader.URLs.find { it.toString().contains('h2') } as URL
  path(id: 'driverPath') {
    pathelement(location: h2.file)
  }
  sql(
    driver:     "org.h2.Driver",
    url:        "jdbc:h2:mem:AZ",
    userid:     "sa",
    password:   "",
    classpathref: "driverPath",
    // direct printed output into a text file:
    output:     "query.out",
    print:      "yes",      // enable printing of result sets
    showheaders:"false",    // suppress column names
    showtrailers:"false"    // suppress "N rows returned" line
  ) {
    transaction("""
      CREATE TABLE some_table (
        id   INT,
        name VARCHAR(200)
      );
    """)
    transaction("""
      INSERT INTO some_table (id, name)
      VALUES (1, 'hello');
    """)
    transaction("""
      SELECT name
      FROM some_table
      WHERE id = 1;
    """)
  }
  // now the file query.out contains exactly "hello"
  loadfile(property: "row1col1", srcFile: "query.out")
  println "row1col1 = ${antProject.getProperty('row1col1')}"
  delete file: "query.out"
}
