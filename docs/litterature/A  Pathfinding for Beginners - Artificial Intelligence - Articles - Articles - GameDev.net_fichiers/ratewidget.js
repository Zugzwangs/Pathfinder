var _ratewidget = window.IPBoard;

_ratewidget.prototype.ratewidget = {
    
    database_id : 0,
    
	init: function()
	{
		Debug.write("Initializing ratewidget.js");
		
		document.observe("dom:loaded", function(){
    
    		var cards = $$('.votecard div');
    		
    		cards.each(function(card) {
    		    var ihtml = card.innerHTML;
    
    			card.down(1).innerHTML = parseInt(card.down(1).innerHTML) + 1;
    			card.innerHTML = ihtml + card.innerHTML ;
    			
    		});
    		
    		var voteactions = $$('.voteaction_dislike');
    		voteactions.each(function(voteaction) {
    			voteaction.observe('click', function(event) {
    				event.stop();
    				
    				if (!voteaction.hasClassName('vdisabled'))
    				{
    					var tid = this.id.replace("vote_","");
                        ipb.ratewidget.doContentRating (tid, 0);				
    					
    					ipb.ratewidget.flip(voteaction, "down");
    				}
    			});
    		});
    	
    		var voteactions = $$('.voteaction_like');
    		voteactions.each(function(voteaction) {
    			voteaction.observe('click', function(event) {
    				event.stop();
    				
    				if (!voteaction.hasClassName('vdisabled'))
    				{
    					var tid = this.id.replace("vote_","");
    					ipb.ratewidget.doContentRating (tid, 5);				    					
    					ipb.ratewidget.flip(voteaction, "up");
    				}
    			});
    		});	
            	
        });
	},
	
    doContentRating : function (record, rating) 
    {
        rating = rating > 1 ? 5 : 0;
    
         var url = ipb.vars['base_url'] + "app=ccs&module=ajax&section=rate&md5check=" + ipb.vars['secure_hash'] + "&id=" + parseInt(ipb.ratewidget.database_id) + "&record=" + parseInt(record) + "&rating=" + parseInt( rating );
    
         new Ajax.Request( url,
    						{
    							method: 'post',
    							evalJSON: 'force',
    							onSuccess: function(t)
    							{
    								// alert(t.responseText); 
    								if( Object.isUndefined( t.responseJSON ) )
    								{
    									alert( "Bad Request" + t.responseJSON );
    								}
    								else if ( t.responseJSON['error'] )
    								{
    									alert( t.responseJSON['error'] );
    								}
    
    							}
    					       });	
    },

    flip : function (obj, vote) 
    {
    			
    			if (vote == "up")
    			{
    				obj.next('div').down(4).innerHTML = parseInt(obj.next('div').down(2).innerHTML) + 1;
    				obj.next('div').down(1).slideUp ({ duration: 0.2 });
    				obj.toggleClassName("voted_like vdisabled");		
    				obj.next('a').toggleClassName("voted_disabled vdisabled");	
    				obj.writeAttribute('title', 'Thanks for voting!');
    			}
    			else {
    				obj.previous('div').down(4).innerHTML = parseInt(obj.previous('div').down(2).innerHTML) - 1;				
    				obj.previous('div').down(1).slideUp ({ duration: 0.2 });
    				obj.toggleClassName("voted_dislike vdisabled");		
    				obj.previous('a').toggleClassName("voted_disabled_like vdisabled");	
    				obj.writeAttribute('title', 'Thanks for voting!');				
    			}
    }

};

ipb.ratewidget.init();
