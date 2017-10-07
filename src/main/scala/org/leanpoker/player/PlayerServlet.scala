package org.leanpoker.player

import javax.servlet.annotation.WebServlet

import com.google.gson.JsonParser
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import spray.json._

@WebServlet(Array("/"))
class PlayerServlet extends HttpServlet {

    override def doGet(req:HttpServletRequest, resp:HttpServletResponse) {
        resp.getWriter().print("Scala player is running")
    }

    @Override
    override def doPost(req:HttpServletRequest, resp:HttpServletResponse) {
        req.getParameter("action") match {
          case "bet_request" =>
            val gameState = req.getParameter("game_state")
            resp.getWriter().print(Player.betRequest(gameState.parseJson))
          case "showdown" =>
            val gameState = req.getParameter("game_state")
            Player.showdown(gameState.parseJson)
          case _ =>
            resp.getWriter().print(Player.VERSION)
        }
    }
}
