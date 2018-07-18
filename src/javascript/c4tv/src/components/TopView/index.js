import React, { Component } from 'react';
import { connect } from 'react-redux';

import './style.css';

import Column from './column';

class TopView extends Component {

  render () {

    let placeHereBubbleOffset = 60 + this.props.hoveredColumn*80;

    //console.log('props', this.props.players);
    //console.log('top View momentan', this.props.topView);
    return (
      <div className="topView">
        <div className="gameHints">
          {this.props.loading ? "Loading, please wait" : "Click on a square to place a block on top of the column. \n Try to connect 4 while looking from above."}
         </div>
        {
          this.props.numberOfColums.map((column, index) => {

            let player = this.props.players[this.props.topView[index]];
            //console.log("player", player);

            return (
              <Column className="column"
                      index={index}
                      hovered={this.props.hoverArray && index === this.props.hoveredColumn}
                      color={player ? player.color : ''}
                      id={player ? player.id : ''}
                      //color={this.props.hoveredColumn}
              >
              </Column>
            )
          })
        }
      </div>
    )
  }

}

const mapStateToProps = (state) => {
  return {
    numberOfColums: state.gameState.topView,
    hoveredColumn: state.gameState.hoveredColumn,
    topView: state.gameState.topView,
    players: state.gameSettings.players,
    loading: state.gameState.loading
  }
}

export default connect(mapStateToProps)(TopView);
