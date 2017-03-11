import React, { Component } from 'react';
import { connect } from 'react-redux';

import * as actions from './../../store/actions';

import './style.css';

class TDColumn extends Component {

  render () {
    return (
      <div  style={{backgroundColor:this.props.hovered ? this.props.hoverColor : ''}}
            className="column"
            onMouseEnter={() => this.props.applyHoverStyle(this.props.index)}
            onMouseLeave={() => this.props.resetHoverStyle()}
            onClick={() => this.props.chooseColumn(this.props.index)}
      />
    )
  }
}

const mapStateToProps = (state) => {
  return {
    hoverColor: state.gameSettings.players[state.gameSettings.activePlayer].hoverColor
  }
}


const mapDispatchToProps = (dispatch) => {
  return {
    applyHoverStyle: (index) => dispatch(actions.setHoverStyle(index)),
    resetHoverStyle: () => dispatch(actions.resetHoverStyle()),
    chooseColumn: (index) => dispatch(actions.chooseColumn(index))
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(TDColumn);
