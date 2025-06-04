// npm run build:css
// echo "build css done"
// npm run generate:api
// npm run compile:ts
// node fixcode.js
const {exec} = require('child_process');

async function runAsync(cmd) {
    return new Promise((resolve, reject) => {
        exec(cmd, (err, stdout, stderr) => {
            if (err) {
                reject(err)
            } else {
                resolve(stdout)
            }
        })

    })
}


(async () => {
     runAsync('npm run build:css').then(console.log);

    await runAsync('npm run generate:api')
    try {
        await runAsync('npm run compile:ts')
    }catch (e){
    }
    await runAsync('node fixcode.js')

})();