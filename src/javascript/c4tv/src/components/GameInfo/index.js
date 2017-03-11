import React, { Component } from 'react';

import { connect } from 'react-redux';

import PlayerInfo from './PlayerInfo';
import './style.css'

class GameInfo extends Component {

  render () {
    return (
      <div className="gameInfo">
       {
        this.props.players.map((player, index) => {
          return <PlayerInfo player={player} key={index} active={index===this.props.currentPlayer}/>
        })
       }
      </div>
    )
  }

}

const mapStateToProps = (state) => {
  return {
    players: state.gameSettings.players,
    currentPlayer: state.gameState.currentPlayer
  }
}

export default connect(mapStateToProps)(GameInfo);
