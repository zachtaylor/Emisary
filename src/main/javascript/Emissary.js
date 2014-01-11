(function(Emissary) {
  var controllers = {},
      views = {};

  Emissary.getController = function(name) {
    return controllers[name] || Emissary.loadController(name);
  };

  Emissary.loadController = function(name) {
    var url = (Emissary.controllerRoot || 'js/') + name.replace('.', '/') + '.js';

    $.ajax({
      url : url,
      dataType: 'script',
      async: false
    });

    return controllers[name];
  };

  Emissary.defineController = function(name, config) {
    var constructor = function Controller() {
      this.class = name;

      if (config.hasOwnProperty('view')) {
        var viewTemplate = config.view,
            viewSelection = $(viewTemplate);

        if (viewSelection.length > 0) {
          this.view = viewSelection;
        }
        else if (viewTemplate) {
          this.view = $(Emissary.getView(viewTemplate)).appendTo('body');
        }
      }
      else {
        this.view = $(Emissary.getView(name)).appendTo('body');
      }

      for (var key in config) {
        if (key === 'view') continue;
        var value = this[key] = config[key];

        if (typeof value === 'string') {
          var selection = $(value, this.view);
          if (selection.length > 0) this[key] = selection;
        }
        else if (typeof value === 'object') {
          this[key] = new (game.getController(value.controller))();
          if (value.selector && this[key].view) $(value.selector, this.view).replaceWith(this[key].view);
        }
      }

      if (config.hasOwnProperty('constructor')) config.constructor.apply(this, arguments);
    };

    for (var fn in config) {
      if (typeof config[fn] === 'function' && fn !== 'constructor') {
        constructor.prototype[fn] = config[fn];
      }
    }

    controllers[name] = constructor;
  };

  Emissary.getView = function(name) {
    return views[name] || Emissary.loadView(name);
  };

  Emissary.loadView = function(name) {
    var url = (Emissary.viewRoot || 'html/') + name.replace('.', '/') + (Emissary.viewExtension || '.html');

    $.ajax({
      url : url,
      async : false,
      success : function(data) {
        views[name] = data;
      }
    });

    return views[name];
  };
})(window.Emissary = window.Emissary || {});