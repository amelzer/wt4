import React, { Component } from 'react';
import { connect } from 'react-redux';

import './style.css';

import TopView from './../TopView';

class Canvas extends Component {

  render () {
    return (
      <div className='canvas' style={{height: this.props.height, width: this.props.width}}>

        <TopView />
      </div>
    )
  }
}


const mapStateToProps = (state) => {
  return {
    width: state.gameSettings.canvasWidth,
    height: state.gameSettings.canvasHeight
  }
}

export default connect(mapStateToProps)(Canvas);

