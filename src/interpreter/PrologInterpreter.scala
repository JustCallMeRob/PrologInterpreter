package interpreter

import parser._
import scala.collection.mutable.Map

class PrologInterpreter(database: Database) {

  var factMap : Map[Atom, List[Atom]] = Map()
  var ruleMap : Map[Fact, List[Fact]] = Map()

  def run() {
    iterate(database.listOfData)
  }

  private def iterate(listOfData: List[Data]): Unit ={

    if(!listOfData.isEmpty){

      listOfData.head match {

        case Fact(head, body) => {
          println("Found new fact.")
          val subject = body(0)
          var listOfFacts = List[Atom]()
          if(factMap.keys.toSeq.contains(subject)){
             listOfFacts = factMap(subject)
          }
          if(body.length > 1){
            for(i <- 1 to body.length - 1){
              listOfFacts = body(i) :: listOfFacts
            }
            listOfFacts = head :: listOfFacts
            factMap(subject) = listOfFacts
          }else{
            listOfFacts = head :: listOfFacts
            factMap(subject) = listOfFacts
          }
          iterate(listOfData.tail)
        }

        case Rule(fact, predicate, predicateList) => {
          println("Found new rule.")
          val newPredicateList = predicate :: predicateList
          ruleMap(fact) = newPredicateList
          iterate(listOfData.tail)
        }
      }
    }

    factMap.foreach(key => println(key._1 + " = " + key._2))
    ruleMap.foreach(key => println(key._1 + " = " + key._2))

    val lineParser = new LineParser
    while(true){
      print("?- ")
      val input = scala.io.StdIn.readLine()
      lineParser.parseAll(lineParser.database, input) match {
        //if the parsing has been successful proceed with interpreting
        case lineParser.Success(r, n) => {
          val lineInterpreter = new LineInterpreter(r, factMap, ruleMap)
          lineInterpreter.run
        }
        //if parsing failed display the error
        case lineParser.Failure(msg, n) => {
          println("Error: " + msg)
        }
      }
    }
  }
}
