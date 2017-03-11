import React, { Component } from 'react';
import { connect } from 'react-redux';

import './style.css';

import TopView from './../TopView';
import GameInfo from './../GameInfo';

class Canvas extends Component {

  constructor (props) {
    super(props);
    this.state = {
      hover: props.showFrontView ? 'flip' : ''
    };
  }

  render () {
    return (
      <div>
      <GameInfo/>
        <div className={"flip-container "+this.state.hover}  onClick={()=>{this.setState({hover: "flip"})}}>
          <div className="flipper">

            <div className='canvas front' style={{height: this.props.height, width: this.props.width}}>
              <TopView />
              <TopView hoverArray />
            </div>

            <div className='canvas back' style={{height: this.props.height, width: this.props.width}}>
              FrontView
            </div>

          </div>
        </div>

      </div>
    )
  }
}


const mapStateToProps = (state) => {
  return {
    width: state.gameSettings.canvasWidth,
    height: state.gameSettings.canvasHeight,
    showFrontView: state.gameState.showFrontView
  }
}

export default connect(mapStateToProps)(Canvas);

