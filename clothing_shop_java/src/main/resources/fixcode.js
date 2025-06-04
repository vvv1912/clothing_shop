const fs  = require('fs');

const js = fs.readFileSync('./static/js/api.js', 'utf8');

// delete import axios from 'axios'; line
const js2 = js.replace("import axios from 'axios';", '');
fs.writeFileSync('./static/js/api.js', js2, 'utf8');