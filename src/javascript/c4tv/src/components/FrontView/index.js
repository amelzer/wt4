import React, { Component } from 'react';
import { connect } from 'react-redux';

import Row from './Row';
class FrontView extends Component {

  render () {

    return (

      <div>
        <div className="winInfo">
         Game ended! ¯\_(ツ)_/¯
        </div>


        <div className="frontView">
          {this.props.frontView ? this.props.frontView.map((column) => {
            return (
              <Row rowData={column}/>
            )
            })
          :
          "Leer"
        }
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
