import React, { Component } from 'react';
import { connect } from 'react-redux';

import './style.css';

import Column from './column';

class TopView extends Component {

  render () {
    return (
      <div className="topView">
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
