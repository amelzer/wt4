import React, { Component } from 'react';

import './style.css';

class PlayerInfo extends Component {
  render () {
    return (
      <div className="playerInfoContainer">

        <div className="playerName">
          {this.props.player.name}
        </div>

        <div className="playerColor" style={{backgroundColor: this.props.player.color}} >
          {this.props.active ? 'active' : ''}
        </div>

      </div>
    )
  }
}


export default PlayerInfo;
