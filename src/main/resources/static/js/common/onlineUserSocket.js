var ws = new WebSocket('ws://' + ip + ":" + port + '/nnn');
ws.onopen = e => {
  	console.log("onopen:",e);
}