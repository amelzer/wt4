import React, { Component } from 'react';
import { connect } from 'react-redux';

import './style.css';

class TopView extends Component {

  render () {
    return (
      <div className="topView">
        {
          this.props.numberOfColums.map((column) => {
            return (
              <div className="column">

              </div>
            )
          })
        }
      </div>
    )
  }

}

const mapStateToProps = (state) => {
  return {
    numberOfColums: state.gameState.topView
  }
}

export default connect(mapStateToProps)(TopView);
