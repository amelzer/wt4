import React, { Component } from 'react';
import { connect } from 'react-redux';

import './style.css';

import Column from './column';

class TopView extends Component {

  render () {

    let placeHereBubbleOffset = 60 + this.props.hoveredColumn*80;

    return (
      <div className="topView">
        <div className="gameHints">Click on a square to place a block on top of the column </div>
        {
          this.props.numberOfColums.map((column, index) => {
            return (
              <Column className="column"
                      index={index}
                      hovered={this.props.hoverArray && index === this.props.hoveredColumn}
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
  }
}

export default connect(mapStateToProps)(TopView);
