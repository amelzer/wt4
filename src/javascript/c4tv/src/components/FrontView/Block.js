import React, { Component } from 'react';
import { connect } from 'react-redux';


import './style.css';

class Block extends Component {
  render () {

    //console.log('cell data', )

    let player = this.props.players[this.props.cellData.player];
    let color = player && player.color;

    return (

      <div className="block" style={{backgroundColor: color}}>
      </div>
    )
  }
}

const mapStateToProps = (state) =>{
  return {
    players: state.gameSettings.players
  }
}

export default connect(mapStateToProps)(Block);
