import React, { Component } from 'react';
import { connect } from 'react-redux';

import Row from './Row';
class FrontView extends Component {

  render () {

    return (

      <div>
        <div className="winInfo">
         {this.props.winner + "won!"}
        </div>


        <div className="frontView">
          {this.props.frontView.map((column) => {
            return (
              <Row rowData={column}/>
            )
          })}
        </div>
      </div>
    )
  }
}


const mapStateToProps = (state) => {
  return {
    frontView: state.gameState.frontView,
    winner: "Player 1"
  }
}

export default connect(mapStateToProps)(FrontView);
