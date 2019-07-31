package interpreter

import parser._
import scala.util.control.Breaks._
import scala.collection.mutable.Map

class LineInterpreter(database: Database, factMap : Map[Atom, List[Atom]], ruleMap : Map[Fact, List[Fact]]) {

  var fact1 = Fact(new Atom(""), List())
  var fact2 = Fact(new Atom(""), List())

  def run() {
    iterate(database.listOfData)
  }

  private def iterate(listOfData: List[Data]): Unit = {

    if (!listOfData.isEmpty) {

      listOfData.head match {

        case Fact(head, body) => {

          if(searchRule(head, body)){
            var checker = false
            for((k,v) <- factMap){
              if(k == body(0) && v.contains(fact1.head) && v.contains(fact2.head)){
                checker = true
              }
            }
            if(checker){
              println("TRUE")
            }else{
              println("FALSE")
            }
          }else{

            val subject = body(0)

            if(!factMap.contains(subject)){
              for((k, v) <- factMap){
                if(v.contains(head) && (v(v.indexOf(head)+1) == body(1))){
                  println(subject.a + " = " + k.a)
                  println("more ? (y/n)")
                  val input = scala.io.StdIn.readLine()
                  if(input.equals("n")){
                    break
                  }
                }
              }
              println("no")
            }else{
              if(body.length == 1){
                if(factMap(subject).contains(head)){
                  println("true")
                }else{
                  println("false")
                }
              }else {
                var checker : Boolean = true
                for (i <- 1 to body.length - 1) {
                  if(!factMap(subject).contains(body(i))){
                    checker = false
                  }
                }
                if(checker){
                  println("true")
                }else{
                  println("false")
                }
              }
            }
          }
        }
      }
    }
  }

  //returns true if rule exists
  def searchRule(head : Atom, body : List[Atom]): Boolean = {

    val fact = new Fact(head, body)

    for ((k, v) <- ruleMap) {
      if(k.head == fact.head){
        fact1 = v(0)
        fact2 = v(1)
        return true
      }
    }
    return false
  }

}