# Staff add-on submission checklist

- [ ] Separate project and unique game key/Velocity plugin ID
- [ ] Targets Java 21 and ERRSA-compatible Velocity/Paper versions
- [ ] Depends on EventBridge; does not package its API classes
- [ ] Add-on appears in EventBridge's wizard; required fields validate
- [ ] Start, stop, cancel and failure cases tested
- [ ] Works without a running player when expected, or explains player carrier requirement
- [ ] Paper actions run on main server thread; no blocking network calls on game threads
- [ ] No hardcoded production credentials or private source code
- [ ] Arena cleanup, inventories, worlds and player disconnects verified
- [ ] New public events appear on the ERRSA Events website
- [ ] Admin approves JAR and tests it on the ERRSA test setup before production
