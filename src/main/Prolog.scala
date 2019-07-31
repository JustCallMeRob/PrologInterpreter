package main

import interpreter.PrologInterpreter
import parser.PrologParser

import scala.io.Source

object Prolog {
  def main(args: Array[String]) {
    val file = Source.fromFile("test.pl")
    val source = file.mkString

    val parser = new PrologParser
    parser.parseAll(parser.database, source) match {
      //if the parsing has been successful proceed with interpreting
      case parser.Success(r, n) => {
        println("Interpretation has begun:")
        val interpreter = new PrologInterpreter(r)
        interpreter.run
      }
      //if parsing failed display the error
      case parser.Failure(msg, n) => {
        println("Error: " + msg)
      }
    }
  }



}
